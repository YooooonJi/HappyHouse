<template>
  <div style="overflow:auto; width:100%; height:100%;">
    <apt-rent-list-item
      v-for="(apt, index) in aptlist"
      :key="index"
      :apt="apt"
      @select-apt="selectApt"
    />
  </div>
</template>

<script>
import AptRentListItem from "./AptRentListItem.vue";

export default {
  name: "AptRentList",
  components: {
    AptRentListItem
  },
  props: {
    aptlist: Array
  },
  methods: {
    selectApt: function(apt) {
      this.$emit("select-apt", apt);
    }
  }
};
</script>

<style></style>
