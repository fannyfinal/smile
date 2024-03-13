/*
 * Copyright (c) 2010-2024 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */
package smile.deep;

import org.bytedeco.pytorch.*;
import org.bytedeco.pytorch.global.torch;

/**
 * A Tensor is a multi-dimensional array containing elements of a single data type.
 *
 * @author Haifeng Li
 */
public class Tensor {
    /** PyTorch Tensor handle. */
    org.bytedeco.pytorch.Tensor value;

    /**
     * Constructor.
     * @param tensor PyTorch Tensor object.
     */
    Tensor(org.bytedeco.pytorch.Tensor tensor) {
        this.value = tensor;
    }

    /**
     * Creates a tensor instance.
     * @param tensor PyTorch Tensor object.
     * @param device the compute device of this Tensor.
     * @param dtype the element data type of this Tensor.
     * @return the tensor instance.
     */
    public static Tensor of(org.bytedeco.pytorch.Tensor tensor, Device device, ScalarType dtype) {
        return new Tensor(tensor.to(device.value, dtype.value));
    }

    /** Prints the tensor on the standard output. */
    public void print() {
        torch.print(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Tensor clone() {
        return new Tensor(value.to());
    }

    /**
     * Returns a new Tensor, detached from the current graph.
     * The result will never require gradient.
     *
     * @return a new Tensor that doesn't require gradient.
     */
    public Tensor detach() {
        return new Tensor(value.detach());
    }

    /**
     * Clone the tensor with a different data type.
     * @param dtype the element data type of new Tensor.
     * @return The cloned tensor.
     */
    public Tensor to(ScalarType dtype) {
        return new Tensor(value.to(dtype.value));
    }

    /**
     * Clone the tensor to a device with a different data type.
     * @param device the compute device of new Tensor.
     * @param dtype the element data type of new Tensor.
     * @return The cloned tensor.
     */
    public Tensor to(Device device, ScalarType dtype) {
        return new Tensor(value.to(device.value, dtype.value));
    }

    /**
     * Returns the element data type.
     * @return the element data type.
     */
    public ScalarType dtype() {
        byte typeValue = value.dtype().toScalarType().value;
        for (ScalarType dtype : ScalarType.values()) {
            if (dtype.value.value == typeValue) {
                return dtype;
            }
        }
        return null;
    }

    /**
     * Returns the shape of the tensor.
     * @return the shape of the tensor.
     */
    public long[] shape() {
        return value.shape();
    }

    /**
     * Returns a tensor with the same data and number of elements
     * but with the specified shape. This method returns a view
     * if shape is compatible with the current shape.
     *
     * @return the tensor with the specified shape.
     */
    public Tensor reshape(long... shape) {
        return new Tensor(value.reshape(shape));
    }

    /** Computes the gradients. */
    public void backward() {
        value.backward();
    }

    /**
     * Returns the int value of element at given index.
     *
     * @param index the element index.
     * @return the element value.
     */
    public int getInt(long... index) {
        org.bytedeco.pytorch.Tensor x = value;
        for (long i : index) {
            x = x.get(i);
        }
        return x.item_int();
    }

    /**
     * Returns the int value of element at given index.
     *
     * @param index the element index.
     * @return the element value.
     */
    public long getLong(long... index) {
        org.bytedeco.pytorch.Tensor x = value;
        for (long i : index) {
            x = x.get(i);
        }
        return x.item_long();
    }

    /**
     * Returns the int value of element at given index.
     *
     * @param index the element index.
     * @return the element value.
     */
    public float getFloat(long... index) {
        org.bytedeco.pytorch.Tensor x = value;
        for (long i : index) {
            x = x.get(i);
        }
        return x.item_float();
    }

    /**
     * Returns the int value of element at given index.
     *
     * @param index the element index.
     * @return the element value.
     */
    public double getDouble(long... index) {
        org.bytedeco.pytorch.Tensor x = value;
        for (long i : index) {
            x = x.get(i);
        }
        return x.item_double();
    }

    /** Returns the int value when the tensor holds a single value. */
    public int toInt() {
        return value.item_int();
    }

    /** Returns the long value when the tensor holds a single value. */
    public long toLong() {
        return value.item_long();
    }

    /** Returns the float value when the tensor holds a single value. */
    public float toFloat() {
        return value.item_float();
    }

    /** Returns the double value when the tensor holds a single value. */
    public double toDouble() {
        return value.item_double();
    }

    /**
     * Returns the indices of the maximum value of a tensor across a dimension.
     *
     * @param dim the dimension to reduce.
     * @param keepDim whether the output tensor has dim retained or not.
     * @return the indices of the maximum value of a tensor across a dimension.
     */
    public Tensor argmax(int dim, boolean keepDim) {
        return new Tensor(value.argmax(new LongOptional(dim), keepDim));
    }

    /**
     * Computes element-wise equality.
     * @param other the tensor to compare.
     * @return the output tensor.
     */
    public Tensor eq(Tensor other) {
        return new Tensor(value.eq(other.value));
    }

    /**
     * Returns the sum of all elements in the tensor.
     * @return the sum of all elements.
     */
    public Tensor sum() {
        return new Tensor(value.sum());
    }

    /**
     * Returns A + b.
     * @param other a scalar value.
     * @return the output tensor.
     */
    public Tensor add(float other) {
        return new Tensor(value.add(new Scalar(other)));
    }

    /**
     * A += b.
     * @param other a scalar value.
     * @return this tensor.
     */
    public Tensor add_(float other) {
        value.add_(new Scalar(other));
        return this;
    }

    /**
     * Returns A + B.
     * @param other another tensor.
     * @return the output tensor.
     */
    public Tensor add(Tensor other) {
        return new Tensor(value.add(other.value));
    }

    /**
     * A += B.
     * @param other another tensor.
     * @return this tensor.
     */
    public Tensor add_(Tensor other) {
        value.add_(other.value);
        return this;
    }

    /**
     * Returns A + alpha * B.
     * @param other another tensor.
     * @param alpha the scaling factor.
     * @return the output tensor.
     */
    public Tensor add(Tensor other, float alpha) {
        return new Tensor(value.add(other.value, new Scalar(alpha)));
    }

    /**
     * A += alpha * B.
     * @param other another tensor.
     * @param alpha the scaling factor.
     * @return this tensor.
     */
    public Tensor add_(Tensor other, float alpha) {
        value.add_(other.value, new Scalar(alpha));
        return this;
    }

    /**
     * Returns A - b.
     * @param other a scalar value.
     * @return the output tensor.
     */
    public Tensor sub(float other) {
        return new Tensor(value.sub(new Scalar(other)));
    }

    /**
     * A -= b.
     * @param other a scalar value.
     * @return this tensor.
     */
    public Tensor sub_(float other) {
        value.sub_(new Scalar(other));
        return this;
    }

    /**
     * Returns A - B.
     * @param other another tensor.
     * @return the output tensor.
     */
    public Tensor sub(Tensor other) {
        return new Tensor(value.sub(other.value));
    }

    /**
     * A -= B.
     * @param other another tensor.
     * @return this tensor.
     */
    public Tensor sub_(Tensor other) {
        value.sub_(other.value);
        return this;
    }

    /**
     * Returns A - alpha * B.
     * @param other another tensor.
     * @param alpha the scaling factor.
     * @return the output tensor.
     */
    public Tensor sub(Tensor other, float alpha) {
        return new Tensor(value.sub(other.value, new Scalar(alpha)));
    }

    /**
     * A -= alpha * B.
     * @param other another tensor.
     * @param alpha the scaling factor.
     * @return this tensor.
     */
    public Tensor sub_(Tensor other, float alpha) {
        value.sub_(other.value, new Scalar(alpha));
        return this;
    }

    /**
     * Multiplies each element of the tensor by the corresponding element of other.
     * @param other another tensor.
     * @return the output tensor.
     */
    public Tensor mul(Tensor other) {
        return new Tensor(value.mul(other.value));
    }

    /**
     * Multiplies each element of the tensor by the corresponding element of other in place.
     * @param other another tensor.
     * @return this tensor.
     */
    public Tensor mul_(Tensor other) {
        value.mul_(other.value);
        return this;
    }

    /**
     * Divides each element of the tensor by the corresponding element of other.
     * @param other another tensor.
     * @return the output tensor.
     */
    public Tensor div(Tensor other) {
        return new Tensor(value.div(other.value));
    }

    /**
     * Divides each element of the tensor by the corresponding element of other in place.
     * @param other another tensor.
     * @return this tensor.
     */
    public Tensor div_(Tensor other) {
        value.div_(other.value);
        return this;
    }

    /**
     * Returns a new tensor with the cosine of the elements of input.
     * @return a new tensor with the cosine of the elements of input.
     */
    public Tensor cos() {
        return new Tensor(value.cos());
    }

    /**
     * Computes the cosine of the elements of input in place.
     * @return this tensor.
     */
    public Tensor cos_() {
        value.cos_();
        return this;
    }

    /**
     * Returns a new tensor with the sine of the elements of input.
     * @return a new tensor with the sine of the elements of input.
     */
    public Tensor sin() {
        return new Tensor(value.cos());
    }

    /**
     * Computes the sine of the elements of input in place.
     * @return this tensor.
     */
    public Tensor sin_() {
        value.cos_();
        return this;
    }

    /**
     * Returns a new tensor with the arccosine of the elements of input.
     * @return a new tensor with the arccosine of the elements of input.
     */
    public Tensor acos() {
        return new Tensor(value.acos());
    }

    /**
     * Computes the arccosine of the elements of input in place.
     * @return this tensor.
     */
    public Tensor acos_() {
        value.acos_();
        return this;
    }

    /**
     * Returns a new tensor with the arcsine of the elements of input.
     * @return a new tensor with the arcsine of the elements of input.
     */
    public Tensor asin() {
        return new Tensor(value.acos());
    }

    /**
     * Computes the arcsine of the elements of input in place.
     * @return this tensor.
     */
    public Tensor asin_() {
        value.acos_();
        return this;
    }

    /**
     * Returns an identity matrix.
     * @param shape the dimension of the resulting matrix.
     * @return the created tensor.
     */
    public static Tensor eye(long shape) {
        return new Tensor(torch.eye(shape));
    }

    /**
     * Returns an identity matrix.
     * @param options Tensor creation options.
     * @param shape the dimension of the resulting matrix.
     * @return the created tensor.
     */
    public static Tensor eye(Options options, long shape) {
        return new Tensor(torch.eye(shape, options.value));
    }

    /**
     * Returns a tensor filled with all zeros.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor zeros(long... shape) {
        return new Tensor(torch.zeros(shape));
    }

    /**
     * Returns a tensor filled with all zeros.
     * @param options Tensor creation options.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor zeros(Options options, long... shape) {
        return new Tensor(torch.zeros(shape, options.value));
    }

    /**
     * Returns a tensor filled with all ones.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor ones(long... shape) {
        return new Tensor(torch.ones(shape));
    }

    /**
     * Returns a tensor filled with all ones.
     * @param options Tensor creation options.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor ones(Options options, long... shape) {
        return new Tensor(torch.ones(shape, options.value));
    }

    /**
     * Returns a tensor filled with values drawn from a uniform distribution on [0, 1).
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor rand(long... shape) {
        return new Tensor(torch.rand(shape));
    }

    /**
     * Returns a tensor filled with values drawn from a uniform distribution on [0, 1).
     * @param options Tensor creation options.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor rand(Options options, long... shape) {
        return new Tensor(torch.rand(shape, options.value));
    }

    /**
     * Returns a tensor filled with values drawn from a unit normal distribution.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor randn(long... shape) {
        return new Tensor(torch.randn(shape));
    }

    /**
     * Returns a tensor filled with values drawn from a unit normal distribution.
     * @param options Tensor creation options.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor randn(Options options, long... shape) {
        return new Tensor(torch.randn(shape, options.value));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(byte[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(short[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(int[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(long[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(float[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * Returns a tensor with given data and shape.
     * @param data the initialization data.
     * @param shape the dimensional shape of the resulting tensor.
     * @return the created tensor.
     */
    public static Tensor of(double[] data, long... shape) {
        return new Tensor(org.bytedeco.pytorch.Tensor.create(data, shape));
    }

    /**
     * A class that encapsulates the construction axes of a Tensor.
     * With construction axis we mean a particular property of a Tensor
     * that can be configured before its construction (and sometimes changed
     * afterwards).
     */
    public static class Options {
        /** PyTorch options object. */
        TensorOptions value;
        /** Constructor with default values for every axis. */
        public Options() {
            this.value = new TensorOptions();
        }

        /** Sets the data type of the elements stored in the tensor. */
        public Options dtype(ScalarType type) {
            value = value.dtype(new ScalarTypeOptional(type.value));
            return this;
        }

        /**
         * Sets a compute device on which a tensor is stored.
         * @param device a compute device.
         * @return this options object.
         */
        public Options device(Device device) {
            value = value.device(new DeviceOptional(device.value));
            return this;
        }

        /** Sets strided (dense) or sparse tensor. */
        public Options layout(Layout layout) {
            value = value.layout(new LayoutOptional(layout.value));
            return this;
        }

        /** Set true if gradients need to be computed for this Tensor. */
        public Options requireGradients(boolean required) {
            value = value.requires_grad(new BoolOptional(required));
            return this;
        }
    }
}
