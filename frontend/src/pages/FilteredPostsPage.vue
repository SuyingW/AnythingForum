<script setup>
import { api } from "boot/axios";
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const $route = useRoute();

const postColumns = [
  {
    name: "postID",
    label: "postID",
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
    name: "alias",
    label: "Writer",
    field: "alias",
    align: "left",
    sortable: true,
  },
  {
    name: "category",
    label: "Category",
    field: "categoryName",
    align: "left",
    sortable: true,
  },
  {
    name: "actions",
    label: "Actions",
    align: "left",
  },
];

const filteredPosts = ref([]);

api
  .get(`/posts/${$route.params.id}`)
  .then((response) => {
    filteredPosts.value = response.data.post;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

</script>

<template>
  <q-page padding>
    <div class="text-h5">Filtered Posts</div>

    <q-card class="posts-container">
      <q-table
      :columns="postColumns"
      :rows="filteredPosts"
      row-key="postID"
      >
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
          </q-td>
        </template>
      </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.posts-container {
  margin-top: 30px;
}
</style>

