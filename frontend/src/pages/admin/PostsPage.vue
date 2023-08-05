<script setup>
import { api } from "boot/axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

const $router = useRouter();

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

function showContent(postID) {
  $router.push({ name: "post", params: { id: postID } });
}

</script>

<template>
  <q-page padding>
    <div class="text-h5">Posts</div>

    <q-card class="posts-container">
      <q-table
      :columns="postColumns"
      :rows="posts"
      row-key="postID"
      >
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn
            color="primary"
            flat
            @click="showContent(props.row.postID)"
            label="View Content"
            />
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
