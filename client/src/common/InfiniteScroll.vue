<template>
  <infinite-loading
    ref="scroll"
    spinner="spiral"
    @infinite="loadNextPage"
  >
    <div slot="no-more"/>
    <div slot="no-results">
      <no-data-info></no-data-info>
    </div>
  </infinite-loading>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
import NoDataInfo from '../common/NoDataInfo'

export default {
  name: 'InfiniteScroll',
  components: {
    InfiniteLoading,
    NoDataInfo,
  },
  methods: {
    loadNextPage($loadingState) {
      this.$emit('load-next-page', $loadingState);
    },
    reset() {
      this.$refs.scroll.$emit('$InfiniteLoading:reset');
    }
  },
}
</script>

<style scoped>
  .icon {
    margin-right: 20px;
  }

  .no-results {
    margin: auto;
    width: 30%;
    font-size: 20px;
  }
</style>