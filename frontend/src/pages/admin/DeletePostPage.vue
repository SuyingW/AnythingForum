<!-- <script setup>
import { api } from "boot/axios";
import { alignPropType } from "react-bootstrap/esm/types";
import { ref } from "vue";
import { useRouter } from "vue-router";

const postID = ref("");

function deletePost() {
  api.delete(`/post/${}`)
}


</script>

<template>
  <q-page>
    <div class="deletepost-container">
      <q-card class="deletepost-card">
        <q-card-section>
          <div class="tex-h6">Delete Post</div>
          <q-input
            class="q-my-md"
            outlined
            v-model="postID"
            label="postID"
          />
          <q-btn
            class="q-mt-md"
            color="primary"
            label="Filter"
            @click="deletePost"
          />
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<style lang="scss" scoped>
.filterposts-container {
  margin-top: 30px;
}
</style> -->
