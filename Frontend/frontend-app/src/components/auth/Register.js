import '../../css/Register.css';
import api from '../../api/api';
import {useState} from 'react';

export default function Register()
{
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [cnp, setCNP] = useState("");

    async function submitData(event){
        event.preventDefault();

        const result = {
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            cnp
        }

        await api.register(result);

        window.location.href = "http://localhost:3000/Login"
    }

    return(
        <div className="registerBody1">
            <div className = "registerWrapper1">

                <div className="filler1">
                    <h2 className='joinHeader1'>Join us</h2>
                    <h3>Welcome to the medical application for all your needs!</h3>
                </div>
            
                <form onSubmit = {submitData} className='registerForm1'>
                    <h3 className='registerHeader1'>Register here</h3>
                    <input placeholder = "Email.."
                    onChange = {(event) => {setEmail(event.target.value)}}></input>
                    <input placeholder = "CNP.."
                    onChange = {(event) => {setCNP(event.target.value)}}></input>
                    <input placeholder="Password.."
                    type="password"
                    onChange = {(event) => {setPassword(event.target.value)}}></input>
                    <input placeholder="Firstname"
                    onChange = {(event) => {setFirstName(event.target.value)}}></input>
                    <input placeholder="Lastname.."
                    onChange = {(event) => {setLastName(event.target.value)}}></input>
                    <input placeholder="Phone number.."
                    onChange = {(event) => {setPhoneNumber(event.target.value)}}></input>
                    <button type="submit" className="submitButton1">Register</button>
                    <button className="submitButton1" onClick={event => {event.preventDefault();
                                                                         window.location.href = 'http://localhost:3000/Login' }}>Back</button>
                </form>

            </div>
        </div>           
    )
}