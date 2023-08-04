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
      <div class="text-h4">{{ post.title }}</div>
    </div>
  </q-page>
</template>

<style lang="scss" scoped>

</style>
