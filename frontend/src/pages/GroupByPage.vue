<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const countColumns = [
  {
    name: "categoryName",
    label: "Name",
    field: "categoryName",
    align: "left",
    sortable: true,
  },
  {
    name: "count",
    label: "Count",
    field: "count",
    align: "left",
    sortable: true,
  },
];

const categories = ref([]);

function groupBy(postID) {
  api
    .get("/groupBy")
    .then((response) => {
      categories.value = response.data.counts;
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Posts</div>

    <q-card class="counts-container">
      <q-table
        :columns="countColumns"
        :rows="categories"
        row-key="categoryName"
      >
      </q-table>
    </q-card>

    <q-btn
      class="q-mt-md"
      color="primary"
      label="Count Group By Category"
      @click="groupBy"
    />
  </q-page>
</template>

<style lang="scss" scoped>
.counts-container {
  margin-top: 30px;
}
</style>
