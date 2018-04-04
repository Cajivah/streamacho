## SPA app using OpenVidu/Kurento Server

### Docker

#### Windows
```
docker run -p 8443:8443 --rm -e KMS_STUN_IP=stun.l.google.com -e KMS_STUN_PORT=19302 -e openvidu.secret=MY_SECRET -e openvidu.publicurl=https://192.168.99.100:8443/ openvidu/openvidu-server-kms
```

#### Linux
```
docker run -p 8443:8443 --rm -e KMS_STUN_IP=stun.l.google.com -e KMS_STUN_PORT=19302 -e openvidu.secret=MY_SECRET openvidu/openvidu-server-kms
```

### Running
```
gradle booturn
```

Example from [OpenVidu tutorial](http://openvidu.io/docs/tutorials/openvidu-js-java/)
