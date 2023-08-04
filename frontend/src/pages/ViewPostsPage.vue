<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const postColumns = [
  {
    name: "postID",
    label: "ID",
    field: "postID",
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
    name: "userID",
    label: "writer",
    field: "userID",
    align: "left",
    sortable: true,
  },
  {
    name: "categoryID",
    label: "Category",
    field: "categoryID",
    align: "left",
    sortable: true,
  },
];

const posts = ref([]);

api
  .get("/posts")
  .then((response) => {
    posts.value = response.data.posts;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

// function showContent() {
//   api
//     .get("/posts")
//     .then((response) => {
//       posts.value = response.data.posts;
//       console.log(response.data);
//     })
//     .catch((error) => {
//       console.log(error);
//     })
// }
</script>

<template>
  <q-page padding>
    <div class="text-h5">ViewPosts</div>

    <q-card class="posts-container">
      <q-table :columns="postColumns" :rows="posts" row-key="postID">
        <!-- <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn color="primary" flat @click="showContent(props.row.postID)" label="View Content" />
          </q-td>
        </template> -->
      </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.posts-container {
  margin-top: 30px;
}
</style>
