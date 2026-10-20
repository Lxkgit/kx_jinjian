import Vue from "vue";
import Vuex from "vuex";
import app from "./modules/app";
import dict from "./modules/dict";
import user from "./modules/user";
import tagsView from "./modules/tagsView";
import permission from "./modules/permission";
import settings from "./modules/settings";
import getters from "./getters";
import accountBook from "./modules/accountBook";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
const stores = require.context("@/modules", true, /store.js$/);
let modules = stores.keys().reduce((modules, modulePath) => {
  const [moduleName] = modulePath.match(/(?<=-)[A-Za-z]+/);
  const value = stores(modulePath).default;
  modules[moduleName] = value;
  return modules;
}, {});
const store = new Vuex.Store({
  modules: {
    app,
    dict,
    user,
    tagsView,
    permission,
    settings,
    accountBook,
    ...modules,
  },
  getters,
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
      reducer(val) {
        const { app, dict, user, tagsView, permission, settings, ...other } =
          val;
        return {
          accountBook,
          ...other,
        };
      },
    }),
  ],
});

export default store;
