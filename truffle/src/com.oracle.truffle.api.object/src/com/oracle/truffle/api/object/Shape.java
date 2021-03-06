/*
 * Copyright (c) 2012, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 * 
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 * 
 * (a) the Software, and
 * 
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 * 
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 * 
 * This license is subject to the following condition:
 * 
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;

import java.util.EnumSet;
import java.util.List;

/**
 * Shape objects create a mapping of Property objects to Locations. Shapes are immutable; adding or
 * deleting a property yields a new Shape which links to the old one. This allows inline caching to
 * simply check the identity of an object's Shape to determine if the cache is valid. There is one
 * exception to this immutability, the transition map, but that is used simply to assure that an
 * identical series of property additions and deletions will yield the same Shape object.
 *
 * @see DynamicObject
 * @see Property
 * @see Location
 * @since 0.8 or earlier
 */
public abstract class Shape {
    /**
     * Constructor for subclasses.
     *
     * @since 0.8 or earlier
     */
    protected Shape() {
    }

    /**
     * Get a property entry by key.
     *
     * @param key the identifier to look up
     * @return a Property object, or null if not found
     * @since 0.8 or earlier
     */
    public abstract Property getProperty(Object key);

    /**
     * Add a new property in the map, yielding a new or cached Shape object.
     *
     * @param property the property to add
     * @return the new Shape
     * @since 0.8 or earlier
     */
    public abstract Shape addProperty(Property property);

    /**
     * Add or change property in the map, yielding a new or cached Shape object.
     *
     * @return the shape after defining the property
     * @since 0.8 or earlier
     */
    public abstract Shape defineProperty(Object key, Object value, int flags);

    /**
     * Add or change property in the map, yielding a new or cached Shape object.
     *
     * @return the shape after defining the property
     * @since 0.8 or earlier
     */
    public abstract Shape defineProperty(Object key, Object value, int flags, LocationFactory locationFactory);

    /**
     * An {@link Iterable} over the shape's properties in insertion order.
     *
     * @since 0.8 or earlier
     */
    public abstract Iterable<Property> getProperties();

    /**
     * Get a list of properties that this Shape stores.
     *
     * @return list of properties
     * @since 0.8 or earlier
     */
    public abstract List<Property> getPropertyList(Pred<Property> filter);

    /**
     * Get a list of all properties that this Shape stores.
     *
     * @return list of properties
     * @since 0.8 or earlier
     */
    public abstract List<Property> getPropertyList();

    /**
     * Returns all (also hidden) property objects in this shape.
     *
     * @param ascending desired order ({@code true} for insertion order, {@code false} for reverse
     *            insertion order)
     * @since 0.8 or earlier
     */
    public abstract List<Property> getPropertyListInternal(boolean ascending);

    /**
     * Get a filtered list of property keys in insertion order.
     *
     * @since 0.8 or earlier
     */
    public abstract List<Object> getKeyList(Pred<Property> filter);

    /**
     * Get a list of all property keys in insertion order.
     *
     * @since 0.8 or earlier
     */
    public abstract List<Object> getKeyList();

    /**
     * Get all property keys in insertion order.
     *
     * @since 0.8 or earlier
     */
    public abstract Iterable<Object> getKeys();

    /**
     * Get an assumption that the shape is valid.
     *
     * @since 0.8 or earlier
     */
    public abstract Assumption getValidAssumption();

    /**
     * Check whether this shape is valid.
     *
     * @since 0.8 or earlier
     */
    public abstract boolean isValid();

    /**
     * Get an assumption that the shape is a leaf.
     *
     * @since 0.8 or earlier
     */
    public abstract Assumption getLeafAssumption();

    /**
     * Check whether this shape is a leaf in the transition graph, i.e. transitionless.
     *
     * @since 0.8 or earlier
     */
    public abstract boolean isLeaf();

    /**
     * @return the parent shape or {@code null} if none.
     * @since 0.8 or earlier
     */
    public abstract Shape getParent();

    /**
     * Check whether the shape has a property with the given key.
     *
     * @since 0.8 or earlier
     */
    public abstract boolean hasProperty(Object key);

    /**
     * Remove the given property from the shape.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape removeProperty(Property property);

    /**
     * Replace a property in the shape.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape replaceProperty(Property oldProperty, Property newProperty);

    /**
     * Get the last added property.
     *
     * @since 0.8 or earlier
     */
    public abstract Property getLastProperty();

    /** @since 0.8 or earlier */
    public abstract int getId();

    /**
     * Append the property, relocating it to the next allocated location.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape append(Property oldProperty);

    /**
     * Obtain an {@link Allocator} instance for the purpose of allocating locations.
     *
     * @since 0.8 or earlier
     */
    public abstract Allocator allocator();

    /**
     * Get number of properties in this shape.
     *
     * @since 0.8 or earlier
     */
    public abstract int getPropertyCount();

    /**
     * Get the shape's operations.
     *
     * @since 0.8 or earlier
     */
    public abstract ObjectType getObjectType();

    /**
     * Get the root shape.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape getRoot();

    /**
     * Check whether this shape is identical to the given shape.
     *
     * @since 0.8 or earlier
     */
    public abstract boolean check(DynamicObject subject);

    /**
     * Get the shape's layout.
     *
     * @since 0.8 or earlier
     */
    public abstract Layout getLayout();

    /**
     * Get the shape's shared data.
     *
     * @since 0.8 or earlier
     */
    public abstract Object getSharedData();

    /**
     * Query whether the shape has a transition with the given key.
     *
     * @since 0.8 or earlier
     */
    public abstract boolean hasTransitionWithKey(Object key);

    /**
     * Clone off a separate shape with new shared data.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape createSeparateShape(Object sharedData);

    /**
     * Change the shape's type, yielding a new shape.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape changeType(ObjectType newOps);

    /**
     * Reserve the primitive extension array field.
     *
     * @since 0.8 or earlier
     */
    public abstract Shape reservePrimitiveExtensionArray();

    /**
     * Create a new {@link DynamicObject} instance with this shape.
     *
     * @since 0.8 or earlier
     */
    public abstract DynamicObject newInstance();

    /**
     * Create a {@link DynamicObjectFactory} for creating instances of this shape.
     *
     * @since 0.8 or earlier
     */
    public abstract DynamicObjectFactory createFactory();

    /**
     * Get mutex object shared by related shapes, i.e. shapes with a common root.
     *
     * @since 0.8 or earlier
     */
    public abstract Object getMutex();

    /**
     * Are these two shapes related, i.e. do they have the same root?
     *
     * @param other Shape to compare to
     * @return true if one shape is an upcast of the other, or the Shapes are equal
     * @since 0.8 or earlier
     */
    public abstract boolean isRelated(Shape other);

    /**
     * Try to merge two related shapes to a more general shape that has the same properties and can
     * store at least the values of both shapes.
     *
     * @return this, other, or a new shape that is compatible with both shapes
     * @since 0.8 or earlier
     */
    public abstract Shape tryMerge(Shape other);

    /**
     * Whether this shape is {@link Shape#makeSharedShape() shared}.
     *
     * @since 0.18
     */
    public boolean isShared() {
        return false;
    }

    /**
     * Make a shared variant of this shape, to allow safe usage of this object between threads.
     * Shared shapes will not reuse storage locations for other fields. In combination with careful
     * synchronization on writes, this can prevent reading out-of-thin-air values.
     *
     * @return a cached and shared variant of this shape
     * @since 0.18
     */
    public Shape makeSharedShape() {
        return null;
    }

    /**
     * Utility class to allocate locations in an object layout.
     *
     * @since 0.8 or earlier
     */
    public abstract static class Allocator {
        /**
         * @since 0.8 or earlier
         */
        protected Allocator() {
        }

        /** @since 0.8 or earlier */
        protected abstract Location locationForValue(Object value, boolean useFinal, boolean nonNull);

        /**
         * Create a new location compatible with the given initial value.
         *
         * @param value the initial value this location is going to be assigned
         * @since 0.8 or earlier
         */
        public final Location locationForValue(Object value) {
            return locationForValue(value, false, value != null);
        }

        /**
         * Create a new location compatible with the given initial value.
         *
         * @param value the initial value this location is going to be assigned
         * @param modifiers additional restrictions and semantics
         * @since 0.8 or earlier
         */
        public final Location locationForValue(Object value, EnumSet<LocationModifier> modifiers) {
            assert value != null || !modifiers.contains(LocationModifier.NonNull);
            return locationForValue(value, modifiers.contains(LocationModifier.Final), modifiers.contains(LocationModifier.NonNull));
        }

        /** @since 0.8 or earlier */
        protected abstract Location locationForType(Class<?> type, boolean useFinal, boolean nonNull);

        /**
         * Create a new location for a fixed type. It can only be assigned to values of this type.
         *
         * @param type the Java type this location must be compatible with (may be primitive)
         * @since 0.8 or earlier
         */
        public final Location locationForType(Class<?> type) {
            return locationForType(type, false, false);
        }

        /**
         * Create a new location for a fixed type.
         *
         * @param type the Java type this location must be compatible with (may be primitive)
         * @param modifiers additional restrictions and semantics
         * @since 0.8 or earlier
         */
        public final Location locationForType(Class<?> type, EnumSet<LocationModifier> modifiers) {
            return locationForType(type, modifiers.contains(LocationModifier.Final), modifiers.contains(LocationModifier.NonNull));
        }

        /**
         * Creates a new location from a constant value. The value is stored in the shape rather
         * than in the object.
         *
         * @since 0.8 or earlier
         */
        public abstract Location constantLocation(Object value);

        /**
         * Creates a new declared location with a default value. A declared location only assumes a
         * type after the first set (initialization).
         *
         * @since 0.8 or earlier
         */
        public abstract Location declaredLocation(Object value);

        /**
         * Reserves space for the given location, so that it will not be available to subsequently
         * allocated locations.
         *
         * @since 0.8 or earlier
         */
        public abstract Allocator addLocation(Location location);

        /**
         * Creates an copy of this allocator state.
         *
         * @since 0.8 or earlier
         */
        public abstract Allocator copy();
    }

    /**
     * Represents a predicate (boolean-valued function) of one argument.
     *
     * For Java 7 compatibility (equivalent to Predicate).
     *
     * @param <T> the type of the input to the predicate
     * @since 0.8 or earlier
     */
    public interface Pred<T> {
        /**
         * Evaluates this predicate on the given argument.
         *
         * @param t the input argument
         * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
         * @since 0.8 or earlier
         */
        boolean test(T t);
    }
}
