
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { name: 'index', path: '', component: () => import('pages/IndexPage.vue') },
      { name: 'register', path: 'register', component: () => import('pages/RegistrationPage.vue') },
      { name: 'posts', path: 'posts', component: () => import('pages/ViewPostsPage.vue') },
      {
        path: 'admin',
        children: [
          { name: 'users', path: 'users', component: () => import('pages/admin/UsersPage.vue') },
        ],
      }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
