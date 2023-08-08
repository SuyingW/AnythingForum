<!-- <script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const categoryID = ref("");

const $router = useRouter();


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
</style> -->


<script setup>
import { api } from "boot/axios";
import { ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const posts = ref([]);
const userOptions = ref([]);
const categoryID = ref(null);

api
  .get("/category")
  .then((response) => {
    userOptions.value = response.data.categories.map((category) => {
      return { label: category.categoryName, value: category.categoryID };
    });
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

  watchEffect(() => {
  if (categoryID.value !== null) {
    fetchPosts()
  }
});

function fetchPosts() {
  api
  .get(`/posts/${categoryID.value}`)
  .then((response) => {
    posts.value = response.data.posts;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  })
}

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
    name: "categoryID",
    label: "CategoryID",
    field: "categoryID",
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

</script>

<template>
  <q-page padding>
    <div class="text-h5">Categories</div>
    <div class="user-select">
      <q-select label="Select Category" outlined emit-value map-options :options="userOptions" v-model="categoryID"></q-select>
    </div>
    <q-card class="q-my-md">
      <q-card-section v-if="categoryID === null">
        <div class="text-body1">Please select a user first.</div>
      </q-card-section>
      <q-card-section v-else-if="posts.length === 0">
        <div class="text-body1">This category has no post</div>
      </q-card-section>
      <q-card-section v-else>
        <q-table
          :columns="postColumns"
          :rows="posts"
          row-key="postID"
        >
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
          </q-td>
        </template>
      </q-table>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.user-select {
  margin: 10px 0;
  width: 200px;
}
</style>
