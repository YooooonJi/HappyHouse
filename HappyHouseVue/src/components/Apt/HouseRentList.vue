<template>
  <div style="overflow:auto; width:100%; height:100%;">
    <house-rent-list-item
      v-for="(apt, index) in aptlist"
      :key="index"
      :apt="apt"
      @select-apt="selectApt"
    />
  </div>
</template>

<script>
import HouseRentListItem from "./HouseRentListItem.vue";

export default {
  name: "HouseTradeList",
  components: {
    HouseRentListItem
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
