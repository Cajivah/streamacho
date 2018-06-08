import Vue from 'vue';

export const showErrorToasts = (error) => {
  if (error) {
    if (error.messages) {
      error.messages.forEach(msg => Vue.toasted.global.error_toast({ message: msg }).goAway(2500));
    } else if(error.message) {
      Vue.toasted.global.error_toast({ message: error.message }).goAway(2500)
    }
  }
};

export const showSuccessToasts = (success = {}) => {
  if (success.messages) {
    success.messages.forEach(msg => Vue.toasted.global.success_toast({ message: msg }).goAway(2500));
  } else if(success.message) {
    Vue.toasted.global.success({ message: success.message }).goAway(3500)
  }
};