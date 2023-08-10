<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRoute } from "vue-router";

const $route = useRoute();

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
    name: "categoryName",
    label: "Category",
    field: "categoryName",
    align: "left",
    sortable: true,
  },
];

const posts = ref([]);

api
  .get(`/collections/${$route.params.id}/posts`)
  .then((response) => {
    posts.value = response.data.posts;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });
</script>

<template>
  <q-page padding>
    <div class="text-h5">Collection Content</div>

    <q-card-section v-if="posts.length == 0">
      <div class="no-post">This collection contains no posts.</div>
    </q-card-section>

    <q-card v-else class="posts-container">
      <q-table
        :columns="postsColumns"
        :rows="posts"
        row-key="postID"
        :rows-per-page-options="[0]">
      </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.posts-container {
  margin-top: 30px;
}
</style>
