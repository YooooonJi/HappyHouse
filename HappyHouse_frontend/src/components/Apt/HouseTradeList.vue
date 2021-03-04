<template>
  <div style="overflow:auto; width:100%; height:100%;">
    <house-trade-list-item
      v-for="(apt, index) in aptlist"
      :key="index"
      :apt="apt"
      @select-apt="selectApt"
    />
  </div>
</template>

<script>
import HouseTradeListItem from "./HouseTradeListItem.vue";

export default {
  name: "HouseTradeList",
  components: {
    HouseTradeListItem
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
