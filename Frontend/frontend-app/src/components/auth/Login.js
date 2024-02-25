import {useState} from 'react';
import '../../css/Login.css';
import api from "../../api/api";

export default function Login()
{
    const [cnp, setCNP] = useState("");
    const [password, setPassword] = useState("");


    // Functie pentru logare
    async function submitData(event)
    {
        event.preventDefault();
        const data = {
            cnp: cnp.trim(),
            password: password.trim()
        }
        console.log(data)

        try{
            await api.login(data);
        }
        catch(error)
        {
            alert('Datele de conectare nu sunt corecte!');
        }
    }

    function redirectRegister(event)
    {
        event.preventDefault();
        window.location.href = "http://localhost:3000/register"
    }

    return(
        <div className="loginWrapper">
            <form onSubmit={submitData} className='loginForm'>
                <h2>Login</h2>
                <label>CNP</label>
                <input placeholder = "CNP.."
                onChange = {(event) => {setCNP(event.target.value)}}></input>
                <label>Password</label>
                <input placeholder="Password.."
                type="password"
                onChange = {(event) => {setPassword(event.target.value)}}></input>
                <button type="Submit" className='submitButton'>Log in</button>
                <button className='submitButton' onClick={redirectRegister}>Register</button>  
            </form>
            
        </div>
    )
}