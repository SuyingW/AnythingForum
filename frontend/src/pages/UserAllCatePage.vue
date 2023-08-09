<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const userColumns = [
  {
    name: "userID",
    label: "userID",
    field: "userID",
    align: "left",
    sortable: true,
  },
  {
    name: "userName",
    label: "userName",
    field: "userName",
    align: "left",
    sortable: true,
  },
  {
    name: "email",
    label: "email",
    field: "email",
    align: "left",
    sortable: true,
  },
  {
    name: "alias",
    label: "alias",
    field: "alias",
    align: "left",
    sortable: true,
  },
];

const users = ref([]);

function division(postID) {
  api
    .get("/userallcate")
    .then((response) => {
      users.value = response.data.userallcate;
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Users Make Posts in All Categories</div>

    <q-card class="users-container">
      <q-table
        :columns="userColumns"
        :rows="users"
        row-key="userID"
      >
      </q-table>
    </q-card>

    <q-btn
      class="q-mt-md"
      color="primary"
      label="Show Users Make Posts in All Categories"
      @click="division"
    />
  </q-page>
</template>

<style lang="scss" scoped>
.counts-container {
  margin-top: 30px;
}
</style>
