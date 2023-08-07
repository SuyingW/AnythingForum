<script setup>
import { api } from "boot/axios";
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const $route = useRoute();

const user = ref(null);

const alias = ref("");
const userID = ref("");

api
  .get(`/user/${$route.params.id}`)
  .then((response) => {
    user.value = response.data.user;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });


  //et currentUserID = $route.params.id;

  function becomeWriter() {
    api.post("/writer", {
      userID: userID.value,
      alias: alias.value,
    })
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.log(error);
    });
  }

</script>

<template>
  <q-page>
    <div class="alias-container">
      <q-card class="alias-card">
        <q-card-section>
          <div class="tex-h6">Add alias</div>
          <q-input class="q-my-md" outlined v-model="userID" label="userID" />
          <q-input class="q-my-md" outlined v-model="alias" label="Alias" />
          <q-btn
            class="q-mt-md"
            color="primary"
            label="BecomeWriter"
            @click="becomeWriter"
          />
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>
