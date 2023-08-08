<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRoute } from "vue-router";

const $route = useRoute();
const saved = ref(false);

const post = ref("");
const title = ref("");
const content = ref("");

api
  .get(`/post/${$route.params.id}`)
  .then((response) => {
    post.value = response.data.post;
    title.value = response.data.post.title;
    content.value = response.data.post.content;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

function edit() {
  api
    .post(`/editPost/${$route.params.id}`, {
      title: title.value,
      content: content.value,
    })
    .then((response) => {
      if (response.data.success) {
        saved.value = true;
      }
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}
</script>

<template>
  <q-page padding>
    <div class="text-h6 q-mt-md">Edit post</div>
    <q-input
      class="q-mt-md"
      filled
      label="Title"
      v-model="title"
      counter
      maxlength="100"
    />
    <q-input
      class="q-mt-md"
      filled
      type="textarea"
      label="Content"
      v-model="content"
      counter
      maxlength="4000"
    />

    <div v-if="saved">
      <q-btn
        class="q-mt-md"
        color="secondary"
        label="Saved!"
        @click="edit"
      ></q-btn>
    </div>
    <div v-else>
      <q-btn class="q-mt-md" color="primary" label="Save" @click="edit"></q-btn>
    </div>
  </q-page>
</template>

<style lang="scss" scoped></style>
