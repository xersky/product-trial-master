import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MessageService} from "primeng/api";
import {NgIf} from "@angular/common";
import {Button} from "primeng/button";

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    Button
  ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private messageService: MessageService) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]],
    });
  }

  onSubmit(): void {
    if (this.contactForm.valid) {
      this.messageService.add({
        severity: 'success',
        summary: 'Succès',
        detail: 'Demande de contact envoyée avec succès',
      });
      this.contactForm.reset();
    }
  }
}
