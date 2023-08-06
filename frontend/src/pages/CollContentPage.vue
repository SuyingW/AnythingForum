<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRoute } from "vue-router";

//const $route = useRoute();

const postsColumns = [
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
    name: "category",
    label: "Category",
    field: "category",
    align: "left",
    sortable: true,
  },
];

const posts = [
  { postID: "1", title: "a", category: "1" },
  { postID: "2", title: "b", category: "2" },
];

//const posts = ref([]);

/*
api
  .get(`/posts/${$route.params.id}`)
  .then((response) => {
    posts.value = response.data.posts;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });
  */
</script>

<template>
  <q-page padding>
    <div class="text-h5">Collection Content</div>

    <q-card class="posts-container">
      <q-table :columns="postsColumns" :row="posts" row-key="postID"> </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.posts-container {
  margin-top: 30px;
}
</style>
