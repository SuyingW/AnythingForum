<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const columns = [
  {
    name: "userID",
    label: "User ID",
    field: "userID",
    align: "left",
    sortable: true,
  },
  {
    name: "avgViews",
    label: "Average Views",
    field: "avgViews",
    align: "left",
    sortable: true,
  },
];

const result = ref([]);

function getAvgViews() {
  api
    .get("/avgViews")
    .then((response) => {
      result.value = response.data.result;
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Average Post Views For Popular Writers</div>

    <q-card class="results-container">
      <q-table
        :columns="columns"
        :rows="result"
        row-key="userID"
      >
      </q-table>
    </q-card>

    <q-btn
      class="q-mt-md"
      color="primary"
      label="Get Results"
      @click="getAvgViews"
    />
  </q-page>
</template>

<style lang="scss" scoped>
.results-container {
  margin-top: 30px;
}
</style>
