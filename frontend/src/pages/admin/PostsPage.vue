<script setup>
import { api } from "boot/axios";
import { computed, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const $router = useRouter();

const allPostColumns = [
  {
    name: "postID",
    label: "Post ID",
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
    name: "userID",
    label: "User ID",
    field: "userID",
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
    name: "publishDate",
    label: "Publish Date",
    field: "publishDate",
    align: "left",
    sortable: true,
  },
  {
    name: "categoryID",
    label: "Category ID",
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
    name: "content",
    label: "Content",
    field: "content",
    align: "left",
    sortable: true,
  },
  {
    name: "actions",
    label: "",
    align: "left",
  },
];
const columnOptions = [
{
    value: "postID",
    label: "Post ID",
  },
  {
    value: "title",
    label: "Title",
  },
  {
    value: "userID",
    label: "User ID",
  },
  {
    value: "alias",
    label: "Writer",
  },
  {
    value: "publishDate",
    label: "Publish Date",
  },
  {
    value: "categoryID",
    label: "Category ID",
  },
  {
    value: "categoryName",
    label: "Category",
  },
  {
    value: "content",
    label: "Content",
  },
];
const filteredColumns = ref(["postID", "title", "alias", "categoryName"]);
const postColumns = computed(() => allPostColumns.filter(c => !c.field || filteredColumns.value.includes(c.field)));

const posts = ref([]);

const isLoading = ref(true);
watchEffect(() => {
  isLoading.value = true;
  api
    .get(`/posts?columns=${filteredColumns.value.join(",")}`)
    .then((response) => {
      posts.value = response.data.posts;
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    })
    .finally(() => {
      isLoading.value = false;
    });
});

function showContent(postID) {
  $router.push({ name: "post", params: { id: postID } });
}

function editPost(postID) {
  $router.push({ name: "editPost", params: { id: postID } });
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Posts</div>

    <div class="text-body1 q-mt-md">Project selected columns:</div>
    <q-option-group
      v-model="filteredColumns"
      :options="columnOptions"
      type="checkbox"
    />

    <q-card class="posts-container">
      <q-table
        :columns="postColumns"
        :rows="posts"
        row-key="postID"
        :loading="isLoading"
        :rows-per-page-options="[0]"
      >
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <div class="q-gutter-y-md column items-start">
              <q-btn-group outlines>
                <q-btn
                  outline
                  color="primary"
                  @click="editPost(props.row.postID)"
                  label="Edit"
                  icon="update"
                />
                <q-btn
                  outline
                  color="primary"
                  @click="showContent(props.row.postID)"
                  label="View"
                  icon="visibility"
                />
              </q-btn-group>
            </div>
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
