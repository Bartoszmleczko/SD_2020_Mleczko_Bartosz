import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { RegisterService } from '../services/register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm = this.fb.group({
    email: [''],
    password: [''],
    firstName: [''],
    lastName: ['']
  });

  constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) { }

  ngOnInit() {
  }

  register() {
    const registerRequest = {
      email: this.registerForm.get('email').value,
      password: this.registerForm.get('password').value,
      firstName: this.registerForm.get('firstName').value,
      lastName: this.registerForm.get('lastName').value
    };
    this.registerService.register(registerRequest).subscribe(
      data => {
        console.log(data);
      }
    );
    this.router.navigate(['/login']);
  }

}
