<template>
  <b-row>
    <div style="margin:auto;">
      <b-form
        class="navbar-search form-inline m-5"
        :class="
          $route.meta.searchClasses
            ? $route.meta.searchClasses
            : 'navbar-search-light'
        "
        id="navbar-search-main"
      >
        <b-form-group class="mb-0">
          <b-input-group class="input-group-alternative input-group-merge">
            <div class="input-group-prepend">
              <span class="input-group-text"
                ><i class="fas fa-search"></i
              ></span>
            </div>
            <b-form-input
              placeholder="구, 군 단위로 검색해주세요"
              v-model="dongName"
              @keypress.enter="sendDongCode"
              type="text"
            ></b-form-input>
          </b-input-group>
        </b-form-group>
      </b-form>
    </div>
  </b-row>
</template>

<script>
// import axios from "axios";
import http from "../../util/http-common";
export default {
  name: "SearchBar",
  data() {
    return {
      dongName: "",
      dongCode: ""
    };
  },
  methods: {
    sendDongCode: function() {
      console.log(this.dongName);
      http
        .get("dongCode/" + this.dongName)
        .then(response => {
          this.dongCode = response.data;
          console.log(this.dongCode);
          this.$emit("send-dong-code", this.dongCode);
        })
        .catch(error => {
          console.log(error);
        });
      // console.log(this.dongCode);
      // this.$emit('send-dong-code', this.dongCode);
    }
  }
};
</script>

<style></style>
