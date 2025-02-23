import {Component} from "@angular/core";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CardModule} from "primeng/card";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CardModule,
    NgIf
  ],
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  contactForm: FormGroup;
  messageSent = false;

  constructor(private fb: FormBuilder) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }

  submitForm() {
    if (this.contactForm.valid) {
      this.messageSent = true;
      setTimeout(() => this.messageSent = false, 3000); // Hide message after 3 seconds
      this.contactForm.reset();
    }
  }
}
