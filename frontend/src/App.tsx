import './reset.css'
import { Routes, Route } from 'react-router-dom'

import RegisterPage from './pages/RegisterPage'
import CookForMe from './pages/CookForMe'

function App() {
  // const [count, setCount] = useState(0)
  // useEffect(() => {
  //   fetch('/api/test')
  //     .then(response => response.json())
  //     .then(json => setMessage(json.SUCCESS_TEXT))
  // }, [])

  return (
    <Routes>
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/cook-for-me" element={<CookForMe />} />
    </Routes>
  )
}

export default App
