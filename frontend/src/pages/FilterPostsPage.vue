<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const categoryID = ref("");

const $router = useRouter();

//const posts = ref(null);

// api
//   .get(`/posts/${$route.params.id}`)
//   .then((response) => {
//     posts.value = response.data.posts;
//     console.log(response.data);
//   })
//   .catch((error) => {
//     console.log(error);
//   });

// function filterPosts() {
//   api.get(`/filteredPosts/${$route.params.id}`, {
//       categoryID: categoryID.value
//     })
//     .then((response) => {
//       console.log(response);
//     })
//     .catch((error) => {
//       console.log(error);
//     });
// }

function filterPosts(categoryID) {
  $router.push({ name: "filteredPosts", params: { id: categoryID } });
}

</script>

<template>
  <q-page>
    <div class="filterposts-container">
      <q-card class="category-card">
        <q-card-section>
          <div class="tex-h6">Filter Posts by Category</div>
          <q-input
            class="q-my-md"
            outlined
            v-model="categoryID"
            label="categotyID"
          />
          <q-btn
            class="q-mt-md"
            color="primary"
            label="Filter"
            @click="filterPosts(categoryID)"
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
</style>
