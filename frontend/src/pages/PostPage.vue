<script setup>
import { api } from "boot/axios";
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const $route = useRoute();

const post = ref(null);

api
  .get(`/post/${$route.params.id}`)
  .then((response) => {
    post.value = response.data.post;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });
</script>

<template>
  <q-page padding>
    <div v-if="post !== null" class="post-container">
      <h4>{{ post.title }}</h4>
      <div class="post-metadata text-subtitle2">
        <div class="publishDate"><span class="text-grey-8">Published on</span> {{ post.publishDate }}</div>
        <div><span class="text-grey-8">Written by</span> {{ post.alias }}</div>
      </div>
      <div class="post-content text-body1">{{ post.content }}</div>
      <div class="post-comments">
        <div class="text-h6">Comments</div>
        <div>To be added.</div>
      </div>
    </div>
  </q-page>
</template>

<style lang="scss" scoped>
.post-metadata {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  gap: 15px;
}
.post-content {
  margin: 15px 0;
  min-height: 200px;
}
</style>
