import { FcGoogle } from "react-icons/fc";
import "./App.css";
function App() {
  const handleOAuthLogin = async (provider) => {
    window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
  };
  return (
    <div className="App">
      <div onClick={() => handleOAuthLogin("google")}>
        <div>
          <FcGoogle style={{ cursor: "pointer", width: 50, height: 50 }} />
          구글 로그인
        </div>
      </div>
    </div>
  );
}

export default App;
