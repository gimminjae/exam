import {api} from "./hooks/useApi";

const sameUrl = '/api/member'
export const memberService = {
  signUp: (signUpDto) => {
    return api.post(`${sameUrl}`, signUpDto)
  },
  sendEmailCode: (email) => {
    return api.post(`${sameUrl}/email?email=${email}`)
  },
  checkUsername: (username) => {
    return api.get(`${sameUrl}/username?username=${username}`)
  },
  checkNickname: (nickname) => {
    return api.get(`${sameUrl}/nickname?nickname=${nickname}`)
  }
}