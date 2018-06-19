export const streamQuality = {
  low: 'LOW',
  medium: 'MEDIUM',
  high: 'HIGH',
};

export const defaultStreamProps = {
  audioSource: undefined, // The source of audio. If undefined default microphone
  videoSource: undefined, // The source of video. If undefined default webcam
  audio: true,                // Whether you want to transmit audio or not
  video: true,                // Whether you want to transmit video or not
  audioActive: true,          // Whether you want to start the publishing with your audio unmuted or muted
  videoActive: true,          // Whether you want to start the publishing with your video enabled or disabled
  quality: streamQuality.high,          // The quality of your video ('LOW', 'MEDIUM', 'HIGH')
  screen: false,  // true to get your screen as video source instead of your camera
};