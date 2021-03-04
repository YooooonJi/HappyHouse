<template>
  <router-view></router-view>
</template>

<script>
import axios from "axios";
export default {
  created() {
    console.log(localStorage.length);
    if (localStorage.length > 0) {
      this.$store.commit("RESET");
    }
  }
};
</script>
