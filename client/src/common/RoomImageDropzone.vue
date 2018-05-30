<template>
  <div class="room-image-dropzone field">
    <label class="file-label">
      <div class="control">
        <input
          class="file-input"
          type="file"
          name="logo"
          :accept="accept"
          @change="processFile"
        >
        <span
          v-if="image === null"
          class="content"
        >
          <span>
            <slot name="icon"/>
            <br>
            <slot name="header"/>
          </span>
          <span class="description">
            <slot name="description"/>
          </span>
        </span>
        <img
          v-else
          :src="image"
          class="image"
        >
      </div>
    </label>
  </div>
</template>

<script>
export default {
  name: 'RoomImageDropzone',
  props: {
    maxX: {
      type: Number,
      default: 800
    },
    maxY: {
      type: Number,
      default: 800
    },
    accept: {
      type: String,
      default: 'image/*'
    }
  },
  data: () => ({
    image: null,
  }),
  methods: {
    processFile(event) {
      if(event.target.files[0]) {
        const url = URL.createObjectURL(event.target.files[0]);
        const image = new Image();

        image.onload = () => {
          const errors = [];

          if(image.width >= this.maxX) {
            errors.push('width');
          }

          if(image.height >= this.maxY) {
            errors.push('height');
          }

          if(errors.length > 0) {
            this.$emit('error', errors);
            return;
          }

          this.image = url;
          this.$emit('load-image', event.target.files[0]);
        };

        image.src = url;
      }
    }
  }
}
</script>

<style scoped>
  .room-image-dropzone {
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    background-color: white;
    color: gray;
    width: 260px;
    height: 260px;
  }

  .file-label {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    border-radius: 50%;
    border: dotted 1px gray;
    width: 252px;
    height: 252px;
    font-size: 20px;
  }

  .file-label:hover {
    background-color: #f6f6f6;
  }

  .content {
    font-size: 30px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
  }

  .description {
    font-size: 13px;
  }

  .image {
    width: 252px;
    height: 252px;
  }
</style>
