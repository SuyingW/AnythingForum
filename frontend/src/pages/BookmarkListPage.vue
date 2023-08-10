<script setup>
import { api } from "boot/axios";
import { computed, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const $router = useRouter();

const bookmarkLists = ref([]);
const userOptions = ref([]);
const userID = ref(null);
const userName = computed(() => {
  if (userID.value === null) {
    return "";
  }
  const user = userOptions.value.find((user) => user.value === userID.value);
  return user.label;
});

api
  .get("/users")
  .then((response) => {
    userOptions.value = response.data.users.map((user) => {
      return { label: user.userName, value: user.userID };
    });
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

watchEffect(() => {
  if (userID.value !== null) {
    fetchLists()
  }
});

function fetchLists() {
  api
    .get(`/users/${userID.value}/bookmarkLists`)
    .then((response) => {
      bookmarkLists.value = response.data.bookmarkLists;
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}

const newListName = ref("");
function createList() {
  api
    .post(`/users/${userID.value}/bookmarkLists`, {
      listName: newListName.value,
    })
    .then((response) => {
      if (response.data.success) {
        fetchLists();
      }
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}

const showList = ref(false);
const currentList = ref(null);
const listPosts = ref([]);
function fetchPosts() {
  api
    .get(`/bookmarkList/${userID.value}/${currentList.value.listID}/posts`)
    .then((response) => {
      listPosts.value = response.data.posts;
      console.log(response.data);
      showList.value = true;
    })
    .catch((error) => {
      console.log(error);
    });
}
function handleListClick(list) {
  currentList.value = list
  fetchPosts()
}

function goToPost(postID) {
  $router.push({ name: "post", params: { id: postID }})
}
</script>

<template>
  <q-page padding>
    <div class="text-h5">Bookmark Lists</div>
    <div class="user-select">
      <q-select label="Select User" outlined emit-value map-options :options="userOptions" v-model="userID"></q-select>
    </div>
    <q-card class="q-my-md">
      <q-card-section v-if="userID === null">
        <div class="text-body1">Please select a user first.</div>
      </q-card-section>
      <q-card-section v-else-if="bookmarkLists.length === 0">
        <div class="text-body1">This user has no bookmark lists.</div>
      </q-card-section>
      <q-card-section v-else>
        <div class="text-h6 q-mb-sm">{{ userName }}'s Bookmark Lists</div>
        <q-list bordered separator>
          <q-item clickable v-ripple v-for="bookmarkList in bookmarkLists" :key="bookmarkList.listID" @click="handleListClick(bookmarkList)">
            <q-item-section>
              {{ bookmarkList.listName }}
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
    <q-dialog v-model="showList">
      <q-card style="width: 700px; max-width: 80vw;">
        <q-card-section>
          <div class="text-h6 q-mb-sm">{{ currentList.listName }}</div>
          <q-list bordered separator v-if="listPosts.length !== 0">
            <q-item clickable v-ripple v-for="post in listPosts" :key="post.postID" @click="goToPost(post.postID)">
              <q-item-section>
                {{ post.title }}
              </q-item-section>
            </q-item>
          </q-list>
          <div class="text-body1" v-else>
            This list is empty.
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
    <hr />
    <div class="text-h6 q-my-md">New Bookmark List</div>
    <q-input outlined v-model="newListName" label="List Name"></q-input>
    <q-btn class="q-mt-md" color="primary" label="Create" @click="createList"></q-btn>
  </q-page>
</template>

<style lang="scss" scoped>
.user-select {
  margin: 10px 0;
  width: 200px;
}
</style>