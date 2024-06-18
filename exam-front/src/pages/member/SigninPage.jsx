import React, {useState, useCallback} from 'react';
import LabelInput from "../../components/LabelInput";

function SignInPage() {
  const initialLoginForm = {
    username: '',
    password: ''
  }
  const [loginForm, setLoginForm] = useState(initialLoginForm)

  const handleLoginFormChange = useCallback(({id, value}) => {
    setLoginForm(prev => ({...prev, [id]: value}))
  }, [])
  const handleSubmit = useCallback((event) => {
    event.preventDefault()
    if (!loginForm.username.trim()) {
      alert('아이디를 입력하세요')
      return
    }
    if (!loginForm.password.trim()) {
      alert('비밀번호를 입력하세요')
      return
    }

    // call login api
  }, [loginForm])
  return (
    <>
      <form onSubmit={handleSubmit}>
        <LabelInput
          labelText="아이디"
          type="text"
          id="username"
          name="username"
          value={loginForm.username}
          placeholder=""
          onChange={(e) => handleLoginFormChange(e.target)}
        />
        <LabelInput
          labelText="비밀번호"
          type="password"
          id="password"
          name="password"
          value={loginForm.password}
          placeholder=""
          onChange={(e) => handleLoginFormChange(e.target)}
        />
        <button type="submit">로그인</button>
      </form>
    </>
  )
}

export default SignInPage