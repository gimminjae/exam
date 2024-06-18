import {Route, Routes} from 'react-router-dom'
import SignupPage from "../pages/member/SignupPage";
import SigninPage from "../pages/member/SigninPage";


const Home = () => <div>Home Page</div>

function RootRouter() {
  return (
    <Routes>
      <Route path="/" exact element={<Home/>}/>
      <Route path="/sign-up" element={<SignupPage/>}/>
      <Route path="/sign-in" element={<SigninPage/>}/>
    </Routes>
  )
}

export default RootRouter
