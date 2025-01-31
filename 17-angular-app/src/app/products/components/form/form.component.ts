import {
  Component,
  EventEmitter,
  Input,
  input,
  Output,
  output,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './form.component.html',
  styleUrl: './form.component.css',
})
export class FormComponent {
  @Input() product!: Product;

  @Output() productCreated = new EventEmitter<Product>();

  public errorMessage: string = '';
  public successMessage: string = '';

  onSubmit(): void {
    if (this.isValidProduct()) {
      this.productCreated.emit(this.product);
      this.successMessage = 'Product saved successfully!';
      this.resetForm();
    } else {
      this.errorMessage = 'Please fill all required fields correctly.';
    }
  }

  private isValidProduct(): boolean {
    return (
      this.product.name.trim() !== '' &&
      this.product.description.trim() !== '' &&
      this.product.price > 0
    );
  }

  resetForm(): void {
    this.product = {
      id: 0,
      name: '',
      description: '',
      price: 0,
    };
    this.errorMessage = '';
  }

  onCancel(): void {
    this.product = {
      id: 0,
      name: '',
      description: '',
      price: 0,
    };
  }
}
