import {Cookies} from 'react-cookie';

const cookies = new Cookies(); // react-cookie library

const cookie = {
  get(key) {
    return cookies.get(key) || ''
  },
  set(key, value) {
    cookies.set(key, value)
  }
}

export default cookie
