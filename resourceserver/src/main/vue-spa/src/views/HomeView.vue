<script setup>
import {onMounted, ref} from 'vue';

const alerts = ref([]);
const loading = ref(false);
const error = ref(null);
// Track per-item saving state to disable controls while a request is in-flight
const saving = ref({});
const token = new URLSearchParams(window.location.search).get('token');
if (token) {
  window.history.replaceState({}, document.title, '/');
}

async function loadAlerts() {
  loading.value = true;
  error.value = null;
  try {
    const headers = {};
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }
    const res = await fetch('http://localhost:8082/api/alerts', {
        credentials: 'include'
    });
    if (!res.ok) {
      throw new Error(`Failed to load alerts: ${res.status} ${res.statusText}`);
    }
    const data = await res.json();
    alerts.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message ?? 'Unknown error while loading alerts';
  } finally {
    loading.value = false;
  }
}

onMounted(loadAlerts);

function formatDate(d) {
  try {
    return new Date(d).toLocaleString();
  } catch (_) {
    return d;
  }
}

function navigateBack() {
  window.location = 'http://127.0.0.1:8082/api';
}

async function putAlert(alert, onSuccess) {
  try {
    saving.value[alert.id] = true;
    const headers = {
      'Content-Type': 'application/json'
    };
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }
    const res = await fetch(`http://localhost:8081/alerts`, {
      method: 'PUT',
      headers: headers,
      body: JSON.stringify(alert)
    });
    if (!res.ok) {
      throw new Error(`Request failed: ${res.status} ${res.statusText}`);
    }
    onSuccess?.();
  } catch (e) {
    // Surface error to user and propagate
    const msg = e?.message ?? 'Unknown error while updating alert';
    // Keep global error non-blocking; show a quick alert for action failure
    console.error(msg);
    window?.alert?.(msg);
  } finally {
    saving.value[alert.id] = false;
  }
}

async function onUpdateAlert(a, ev) {
  const id = ev?.target?.id;
  a[id] = ev?.target?.checked;
  await putAlert(a,  () => {
    console.log('Alert updated: ', a);
  });
}
</script>

<template>
  <main style="width: 1024px;">
    <section>
      <h1>My Alerts</h1>

      <div v-if="loading">Loading alerts…</div>
      <div v-else-if="error" style="color: #b00020">{{ error }}</div>

      <div v-else style="width: 80%; margin: auto;">
        <div v-if="alerts.length === 0">No alerts found.</div>
        <div v-for="(a, idx) in alerts" :key="a.id ?? idx" style="margin: 0.5rem 0; padding: 0.5rem; border: 1px solid #ddd; border-radius: 6px;">
          <div style="font-weight: 600;">{{ a.message }}</div>
          <div style="font-size: 0.9rem; color: #555;">
            <span>Type: {{ a.type }}</span>
            <span> | Severity: {{ a.severity }}</span>
            <span> | Observed: {{ formatDate(a.dateObserved) }}</span>
            <span> | Resolved: {{ a.resolved ? 'Yes' : 'No' }}</span>
            <span> | False Positive: {{ a.falsePositive ? 'Yes' : 'No' }}</span>
          </div>
          <div style="margin-top: 0.4rem; display: flex; gap: 1rem; align-items: center;">
            <label>
              <input
                id="resolved"
                type="checkbox"
                :checked="a.resolved"
                :disabled="saving[a.id]"
                @change="onUpdateAlert(a, $event)"
              />
              Mark as resolved
            </label>
            <label>
              <input
                id="falsePositive"
                type="checkbox"
                :checked="a.falsePositive"
                :disabled="saving[a.id]"
                @change="onUpdateAlert(a, $event)"
              />
              Mark as false positive
            </label>
          </div>
        </div>
      </div>
      <button @click="navigateBack" style="margin: 1rem;">Back</button>
      <button @click="loadAlerts" style="margin: 1rem;">Refresh</button>
    </section>
  </main>
</template>
