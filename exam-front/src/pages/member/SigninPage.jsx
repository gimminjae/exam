import React, {useState, useCallback} from 'react';

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
        <div>
          <label htmlFor="userId">아이디:</label>
          <input
            type="text"
            id="username"
            value={loginForm.username}
            onChange={(e) => handleLoginFormChange(e.target)}
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            type="password"
            id="password"
            value={loginForm.password}
            onChange={(e) => handleLoginFormChange(e.target)}
          />
        </div>
        <button type="submit">로그인</button>
      </form>
    </>
  )
}

export default SignInPage