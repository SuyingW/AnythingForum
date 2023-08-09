<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const $route = useRoute();
const $router = useRouter();
const leftDrawerOpen = ref(false);

const navigationItems = [
  {
    label: "Home",
    routeName: "index",
  },
  {
    label: "Register",
    routeName: "register",
  },
  {
    label: "Manage Users",
    routeName: "users",
  },
  {
    label: "View Posts",
    routeName: "posts",
  },
  {
    label: "Collections",
    routeName: "collections",
  },
  {
    label: "Bookmark Lists",
    routeName: "bookmarkLists",
  },
  {
    label: "Group By",
    routeName: "groupBy",
  },
  {
    label: "Average Views",
    routeName: "avgViews",
  },
  {
    label: "Potential Spam",
    routeName: "potentialSpam",
  },
  {
    label: "Filter Posts",
    routeName: "filterPosts",
  },
  {
    label: "Delete Post",
    routeName: "delete",
  },
  {
    label: "Users Make Posts in All Categories",
    routeName: "userAllCate",
  },
];


function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}
function handleRouteClick(name) {
  if ($route.name !== name) {
    $router.push({ name });
  }
}
</script>


<template>
  <q-layout view="hHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title> Anything </q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item
          v-for="item in navigationItems"
          :key="item.routeName"
          clickable
          v-ripple
          :active="$route.name === item.routeName"
          active-class="bg-primary text-white"
          @click="handleRouteClick(item.routeName)"
        >
          <q-item-section>
            <q-item-label class="text-weight-medium">{{
              item.label
            }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>
