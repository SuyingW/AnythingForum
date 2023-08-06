<script setup>
import { api } from "boot/axios";
import { ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const bookmarkLists = ref([]);
const userOptions = ref([]);
const userID = ref(null);

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
        <q-list>
          <q-item clickable v-ripple v-for="bookmarkList in bookmarkLists" :key="bookmarkList.listID">
            <q-item-section>
              {{ bookmarkList.listName }}
            </q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
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