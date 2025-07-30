// create-election.component.ts
import { Component, NgModule } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-election',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './create-election.component.html',
})
export class CreateElectionComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      options: this.fb.array([this.fb.control('', Validators.required)]),
      voters: this.fb.array([this.fb.control('', Validators.required)])
    });
  }

  get options() {
    return this.form.get('options') as FormArray;
  }

  get voters() {
    return this.form.get('voters') as FormArray;
  }

  addOption() {
    this.options.push(this.fb.control('', Validators.required));
  }

  removeOption(index: number) {
    this.options.removeAt(index);
  }

  addVoter() {
    this.voters.push(this.fb.control('', Validators.required));
  }

  removeVoter(index: number) {
    this.voters.removeAt(index);
  }

  submit() {
    if (this.form.invalid) return;
    console.log(this.form.value); // ensuite appel au service
  }
}
