<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const $router = useRouter();

const collColumns = [
  {
    name: "collectionID",
    label: "collectionID",
    field: "collectionID",
    align: "left",
    sortable: true,
  },
  {
    name: "title",
    label: "Title",
    field: "title",
    align: "left",
    sortable: true,
  },
  {
    name: "alias",
    label: "Author",
    field: "alias",
    align: "left",
    sortable: true,
  },
  {
    name: "actions",
    label: "Actions",
    align: "left",
  },
];

const colls = ref([]);
/*const colls = ref([
  { collectionID: "1", title: "a", userName: "Diana" },
  { collectionID: "2", title: "b", userName: "Suying" },
]);
*/

api
  .get("/collections")
  .then((response) => {
    colls.value = response.data.collections;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

function showPosts(collectionID) {
  $router.push({ name: "collection", params: { id: collectionID } });
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Posts</div>

    <q-card class="collections-container">
      <q-table :columns="collColumns" :rows="colls" row-key="collectionID">
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn
              color="primary"
              flat
              @click="showPosts(props.row.collectionID)"
              label="View Content"
            />
          </q-td>
        </template>
      </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.collections-container {
  margin-top: 30px;
}
</style>
