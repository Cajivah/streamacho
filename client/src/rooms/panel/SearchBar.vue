<template>
  <div class="search-bar">
    <div class="field has-addons">
      <div class="control is-expanded has-icons-left">
        <input
          :placeholder="placeholder"
          v-model="searchText"
          type="search"
          class="input is-large"
          @keyup.enter="splitTags"
        >
        <span class="icon is-left">
          <i class="fa fa-search fa-sm"/>
        </span>
      </div>
      <div class="control">
        <a
          class="button is-large is-info"
          @click="splitTags"
        >
          Search
        </a>
      </div>
    </div>
    <div class="selected-tags">
      <div class="tags">
        <span
          v-for="(tag, index) in searchTags"
          :key="index"
          class="tag is-medium is-info"
          @click.middle="deleteTag(index)"
        >
          {{ tag }}
          <button
            class="delete-tag delete is-small"
            @click="deleteTag(index)"
          />
        </span>
      </div>
      <a
        v-if="searchTags.length > 0"
        class="fa fa-2x fa-trash has-text-light"
        @click="clearTags"
      />
    </div>
  </div>
</template>

<script>
const splitStringToArray = (string) =>
  string
    .replace(/\s+/g,' ')
    .trim()
    .toLowerCase()
    .split(' ')
    .filter(el => el !== '');

export default {
  name: 'SearchBar',
  props: {
    placeholder: {
      type: String,
      default: '',
    }
  },
  data() {
    return {
      searchText: '',
      searchTags: [],
    }
  },
  methods: {
    splitTags() {
      const tags = splitStringToArray(this.searchText);
      if(tags.length > 0) {
        this.searchTags = [...this.searchTags, ...tags];
        this.search();
        this.searchText = '';
      }
    },

    deleteTag(index) {
      this.searchTags.splice(index, 1);
      if(this.searchTags.length === 0) {
        this.$emit('clear');
      } else {
        this.search();
      }
    },

    search() {
      const query = this.searchTags.join(' ');
      this.$emit('search', query);
    },

    clearTags() {
      this.searchTags = [];
      this.$emit('clear');
    }
  }
}
</script>

<style scoped>
  .search-bar {
    margin: 30px 0;
  }

  .input {
    border: none;
  }

  .selected-tags {
    display: flex;
    justify-content: space-between;
    min-height: 60px;
  }

  .tag {
    display: flex;
    justify-content: space-between;
    min-width: 100px;
  }
</style>