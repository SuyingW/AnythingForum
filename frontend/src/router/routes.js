const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        name: "index",
        path: "",
        component: () => import("pages/IndexPage.vue"),
      },
      {
        name: "register",
        path: "register",
        component: () => import("pages/RegistrationPage.vue"),
      },
      {
        name: "post",
        path: "post/:id",
        component: () => import("pages/PostPage.vue"),
      },
      {
        name: "editPost",
        path: "editPost/:id",
        component: () => import("pages/EditPostPage.vue"),
      },
      {
        name: "collections",
        path: "collections",
        component: () => import("pages/CollectionPage.vue"),
      },
      {
        name: "collectionContent",
        path: "collection/:id",
        component: () => import("pages/CollContentPage.vue"),
      },
      {
        name: "groupBy",
        path: "groupBy",
        component: () => import("pages/GroupByPage.vue"),
      },
      {
        name: "bookmarkLists",
        path: "bookmarkLists",
        component: () => import("pages/BookmarkListPage.vue"),
      },
      {
        name: "filterPosts",
        path: "filterPosts",
        component: () => import("pages/FilterPostsPage.vue"),
      },
      {
        name: "filteredPosts",
        path: "filteredPosts/:id",
        component: () => import("pages/FilteredPostsPage.vue"),
      },

      {
        path: "admin",
        children: [
          {
            name: "users",
            path: "users",
            component: () => import("pages/admin/UsersPage.vue"),
          },
          {
            name: "posts",
            path: "posts",
            component: () => import("pages/admin/PostsPage.vue"),
          },
          {
            name: "user",
            path: "user/:id",
            component: () => import("pages/admin/AliasPage.vue"),
          },
          {
            name: "delete",
            path: "delete",
            component: () => import("pages/admin/DeletePostPage.vue"),
          },
        ],
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
