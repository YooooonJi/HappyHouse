import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

const SERVER_URL = "http://localhost:8080/";

export default new Vuex.Store({
  state: {
    accessToken: null,
    userId: "",
    userName: "",
    userSeq: ""
  },
  getters: {
    getAccessToken(state) {
      return state.accessToken;
    },
    getUserId(state) {
      return state.userId;
    },
    getUserName(state) {
      return state.userName;
    },
    getUserSeq(state) {
      return state.userSeq;
    }
  },
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload["auth-token"];
      state.userId = payload["user-id"];
      state.userName = payload["user-name"];
      state.userSeq = payload["user-seq"];
      localStorage.setItem("token", payload["auth-token"]);
      localStorage.setItem("id", payload["user-id"]);
      localStorage.setItem("name", payload["user-name"]);
      localStorage.setItem("seq", payload["user-seq"]);
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.userId = "";
      state.userName = "";
      state.user = null;
      localStorage.clear();
    },
    RESET(state) {
      state.accessToken = localStorage.getItem("token");
      state.userId = localStorage.getItem("id");
      state.userName = localStorage.getItem("name");
      state.userSeq = localStorage.getItem("seq");
    }
  },
  actions: {
    LOGIN(context, user) {
      return axios
        .post(`${SERVER_URL}user/confirm/login`, user)
        .then(response => {
          context.commit("LOGIN", response.data);
          // axios.defaults.headers.common[
          //   "auth-token"
          // ] = `${response.data["auth-token"]}`;
        });
    },
    LOGOUT(context) {
      context.commit("LOGOUT");
      // axios.defaults.headers.common["auth-token"] = undefined;
    },
    EDITPROFILE(context, user) {
      // let accessToken = this.getAccessToken;
      console.log(this.getAccessToken);
      return axios
        .put(`${SERVER_URL}user/update`, user, {
          headers: {
            "auth-token": localStorage.getItem("token")
          }
        })
        .then(response => {
          context.commit("LOGOUT");
          context.commit("LOGIN", response.data);
          // axios.defaults.headers.common[
          //   "auth-token"
          // ] = `${response.data["auth-token"]}`;
        });
    }
  },
  modules: {}
});
