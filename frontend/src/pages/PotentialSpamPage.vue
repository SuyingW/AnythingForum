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
    name: "numComments",
    label: "Number of Comments Made",
    field: "numComments",
    align: "left",
    sortable: true,
  },
];

const result = ref([]);

function getPotentialSpam() {
  api
    .get("/potentialSpam")
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
    <div class="text-h5">Potential Spam Users</div>
    <div class="text-body1">These users have made more than one comment but do not have any followers.</div>

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
      @click="getPotentialSpam"
    />
  </q-page>
</template>

<style lang="scss" scoped>
.results-container {
  margin-top: 30px;
}
</style>
