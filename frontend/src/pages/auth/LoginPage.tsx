/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable @typescript-eslint/no-use-before-define */
import axios from 'axios'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import kakao from '../../../public/images/auths/btn_kakao.png'
import logo from '../../../public/images/foodlier_logo.png'
import naver from '../../../public/images/auths/btn_naver.png'
import * as S from '../../styles/auth/LoginPage.styled'
import { onLoginSuccess } from '../../services/authServices'
import { palette } from '../../constants/Styles'

const Login = () => {
  const navigate = useNavigate()
  // TODO : env 파일로 옮기기
  const REST_API_KEY = '316fd79d7772bc7314aae06a9013ff8a'
  const REDIRECT_URI = 'http://localhost:5173/naver/callback'
  const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`

  const NAVER_CLIENT_ID = 'wLMPcSoz4xbMttqmCXVD'
  const STATE = 'false'
  const NAVER_AUTH_URI = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&state=${STATE}`

  const KakaoLoginHandler = () => {
    window.location.href = KAKAO_AUTH_URI
  }

  const NaverLoginHandler = () => {
    window.location.href = NAVER_AUTH_URI
  }

  const now = new Date()
  const isoString = now.toISOString()

  const [userInputs, setUserInputs] = useState({
    currentDate: isoString,
    email: '', // 빈 문자열로 초기화
    password: '', // 빈 문자열로 초기화
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setUserInputs({
      ...userInputs,
      [name]: value,
    })
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    axios
      .post('/api/auth/signin', userInputs)
      .then(res => {
        onLoginSuccess(res)
        navigate('/')
      })
      .catch(err => {
        console.log(err.response.data)
        const { errorCode } = err.response.data
        if (errorCode === 'MEMBER_NOT_FOUND') {
          Swal.fire({
            icon: 'error',
            title: '로그인 실패',
            text: '아이디 또는 비밀번호를 확인해주세요.',
            confirmButtonColor: `${palette.main}`,
          })
        } else if (userInputs.email === '' || userInputs.password === '') {
          Swal.fire({
            icon: 'error',
            title: '로그인 실패',
            text: '아이디 또는 비밀번호를 입력해주세요.',
            confirmButtonColor: `${palette.main}`,
          })
        }
      })
  }

  return (
    <S.Container>
      <S.Title>
        <S.Logo src={logo} alt="Foodlier Logo" />
      </S.Title>
      <S.Form onSubmit={handleSubmit}>
        <S.Input
          type="email"
          placeholder="Email"
          name="email"
          value={userInputs.email}
          onChange={handleChange}
        />
        <S.Input
          type="password"
          placeholder="Password"
          name="password"
          value={userInputs.password}
          onChange={handleChange}
        />
        <S.FindPassword>
          <button
            type="button"
            onClick={() => {
              navigate('/find-password')
            }}
          >
            비밀번호 찾기
          </button>
        </S.FindPassword>
        <S.Button type="submit">로그인</S.Button>
        <S.RegisterButton
          onClick={() => {
            navigate('/register')
          }}
        >
          회원가입
        </S.RegisterButton>
      </S.Form>
      <S.Divider>
        <S.Text>OR</S.Text>
      </S.Divider>
      <S.Form>
        <S.SocialLoginButton type="button" onClick={NaverLoginHandler}>
          <S.SocialLogo src={naver} alt="" />
          <span>Sign in With Naver</span>
        </S.SocialLoginButton>
        <S.SocialLoginButton type="button" onClick={KakaoLoginHandler}>
          <S.SocialLogo src={kakao} alt="" />
          <span>Sign in With Kakao</span>
        </S.SocialLoginButton>
      </S.Form>
    </S.Container>
  )
}

export default Login
