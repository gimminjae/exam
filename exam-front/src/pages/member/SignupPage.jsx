import React, {useState, useCallback} from 'react';
import useApi from "../../hooks/useApi";
import {memberService} from "../../core.api.member";
import util from "../../hooks/util";

function SignUpForm() {
  const initialMemberForm = {
    username: '',
    nickname: '',
    email: '',
    password1: '',
    password2: '',
    memType: 'common'
  }
  const initialErrors = {
    username: '',
    nickname: '',
    email: '',
    password1: '',
    password2: ''
  }

  const {callApi: checkUsername} = useApi({
    api: memberService.checkUsername,
    onSuccess: () => handleConfirmYnChange({id: 'username', value: true}),
    onError: (error) => handleErrorsChange({id: 'username', value: util.showBasicError(error)})
  })
  const {callApi: checkNickname} = useApi({
    api: memberService.checkNickname,
    onSuccess: () => handleConfirmYnChange({id: 'nickname', value: true}),
    onError: (error) => handleErrorsChange({id: 'nickname', value: util.showBasicError(error)})
  })

  const [memberForm, setMemberForm] = useState(initialMemberForm)
  const handleMemberFormChange = useCallback(({id, value}) => {
    setMemberForm(prev => ({...prev, [id]: value}))
  }, [])
  const [errors, setErrors] = useState(initialErrors)
  const handleErrorsChange = useCallback(({id, value}) => {
    setErrors(prev => ({...prev, [id]: value}))
  }, [])
  const [confirmYn, setConfirmYn] = useState({
    email: false,
    password: false,
    username: false,
    nickname: false
  })
  const handleConfirmYnChange = useCallback(({id, value}) => {
    setConfirmYn(prev => ({...prev, [id]: value}))
  }, [])

  // 중복 확인 버튼 클릭 시 이벤트 처리
  const handleCheckUsername = useCallback(async () => {
    await checkUsername(memberForm.username)
  }, [memberForm.username])

  const handleCheckNickname = useCallback(async () => {
    await checkNickname(memberForm.nickname)
  }, [memberForm.nickname])

  // 인증코드 전송 버튼 클릭 시 이벤트 처리
  const handleSendEmailCode = useCallback(() => {
    // API 호출하여 이메일 인증코드 전송 로직 구현
  }, [])

  // 회원가입 버튼 클릭 시 이벤트 처리
  const handleSubmit = useCallback((event) => {
    event.preventDefault()

    // 모든 항목에 문자열이 있는지 확인
    if (!memberForm.username) {
      setErrors({id: 'username', value: '아이디를 입력하세요'});
      return;
    }
    if (!memberForm.nickname) {
      setErrors({id: 'nickname', value: '닉네임을 입력하세요'});
      return;
    }
    if (!memberForm.email) {
      setErrors({id: 'email', value: '이메일을 입력하세요'});
      return;
    }
    if (!memberForm.password1 || !memberForm.password2) {
      setErrors({id: 'password1', value: '비밀번호를 입력하세요'});
      return;
    }
    if (memberForm.password1 !== memberForm.password2) {
      setErrors({id: 'password2', value: '비밀번호가 일치하지 않습니다.'});
      return;
    }

    // API 호출하여 회원가입 로직 구현
  }, [])

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="userId">아이디:</label>
        <input
          type="text"
          id="username"
          value={memberForm.username}
          onChange={(e) => handleMemberFormChange(e.target)}
        />
        <button onClick={handleCheckUsername}>중복 확인</button>
        {errors.username && <p className="error">{errors.username}</p>}
      </div>
      <div>
        <label htmlFor="nickname">닉네임:</label>
        <input
          type="text"
          id="nickname"
          value={memberForm.nickname}
          onChange={(e) => handleMemberFormChange(e.target)}
        />
        <button onClick={handleCheckNickname}>중복 확인</button>
        {errors.nickname && <p className="error">{errors.nickname}</p>}
      </div>
      <div>
        <label htmlFor="email">이메일:</label>
        <input
          type="email"
          id="email"
          value={memberForm.email}
          onChange={(e) => handleMemberFormChange(e.target)}
        />
        <button onClick={handleSendEmailCode}>인증코드 전송</button>
        {errors.email && <p className="error">{errors.email}</p>}
      </div>
      <div>
        <label htmlFor="password">비밀번호:</label>
        <input
          type="password"
          id="password"
          value={memberForm.password}
          onChange={(e) => handleMemberFormChange(e.target)}
        />
        {errors.password1 && <p className="error">{errors.password1}</p>}
      </div>
      <div>
        <label htmlFor="confirmPassword">비밀번호 확인:</label>
        <input
          type="password"
          id="confirmPassword"
          value={memberForm.password2}
          onChange={(e) => handleMemberFormChange(e.target)}
        />
        {errors.password2 && <p className="error">{errors.password2}</p>}
      </div>
      <button type="submit">회원가입</button>
    </form>
  );
}

export default SignUpForm;